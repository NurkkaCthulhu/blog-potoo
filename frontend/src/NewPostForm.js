import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import './css/NewPostForm_style.css';

class NewPostForm extends Component {

    constructor(props) {
        super(props);

        this.makeNewPost = this.makeNewPost.bind(this);
        this.deletePost = this.deletePost.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onRouteChanged = this.onRouteChanged.bind(this);

        this.state = {
            author: ''
            , title: ''
            , content: ''
            , tags: ''
        };
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        //const tags = target.tags;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        const author = this.state.author;
        const title = this.state.title;
        if (author.length <= 0 || title.length <= 0) {
            alert('You must give author and title. Please try again.');
        } else {
            this.makeNewPost();
        }

        event.preventDefault();
    }

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) {
            this.onRouteChanged();
        }
    }

    onRouteChanged() {
        this.setState({
            author: ''
            , title: ''
            , content: ''
            , tags: ''
            , modifying: false});
    }

    componentDidMount() {
        if (this.props.match.params.id === 'new') {
            this.setState({modifying: false})
        } else {
            fetch('/api/blogposts/' + this.props.match.params.id)
                .then((httpResponse) => httpResponse.json())
                .then((post) => {
                    let tags = '';
                    for (let t of post.tags) {
                        tags += t.tagName;
                        tags += ',';
                    }
                    tags = tags.slice(0, -1);
                    this.setState({
                        author: post.author
                        , title: post.title
                        , content: post.content
                        , tags: tags
                        , modifying: true
                    });
            });
        }
    }

    async makeNewPost() {
        var tagArray = this.state.tags.split(',');

        for (let i in tagArray) {
            let tag = tagArray[i];
            tagArray[i] = tag.trim();
        }

        let filtered = tagArray.filter(function (el) {
            return el !== '';
        });

        tagArray = filtered;

        if(this.state.modifying){
            const newPost = {
                title: this.state.title,
                content: this.state.content,
                tags: tagArray
            };
            await fetch('/api/blogposts/' + this.props.match.params.id, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newPost),
            }).finally(() => {
                this.props.history.push("/")
            })
        } else {
            const newPost = {
                author: this.state.author,
                title: this.state.title,
                content: this.state.content,
            };
            await fetch('/api/blogposts/', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newPost),
            }).then((response) => {
                return response.json()
            }).then((value) => {
                console.log(value);

                if (tagArray.length > 0) {
                    fetch('/api/blogposts/' + value + '/tag', {
                        method: 'POST',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(tagArray),
                    }).then(() => {
                        console.log("tags added to " + value)
                    })
                }
            }).finally(() => {
                this.props.history.push("/")
            })
        }
    }

    async deletePost() {
        await fetch('/api/blogposts/' + this.props.match.params.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            this.props.history.push('/');
        });
    }

    render() {
        return (
            <div className = "container">
                <div className="newpostheader"><h1>Make a new post</h1></div>
                <form onSubmit={this.handleSubmit}>
                    <div className="required">
                        <label>
                            Author
                            <br />
                            <input type="text" value={this.state.author} onChange={this.handleChange} name="author" readOnly={this.state.modifying}/>
                            <small hidden={!this.state.modifying}>Post author cannot be modified.</small>
                        </label>
                    </div>

                    <br />

                    <div className="required">
                        <label>
                            Title
                            <br />
                            <input type="text" value={this.state.title} onChange={this.handleChange} name="title"/>
                        </label>
                    </div>

                    <br />

                    <label>
                        Content
                        <br />
                        <textarea value={this.state.content} onChange={this.handleChange} name="content"/>
                    </label>

                    <p><label>
                       Tags
                       <br />
                       <input type="text" value={this.state.tags} onChange={this.handleChange} name="tags"/>
                    </label></p>
                    <div className={"row"}>
                        <input type="submit" value="Submit" className="submit-button app-button" />
                        <button className="cancel-button app-button" onClick={() => this.props.history.push('/')}>Cancel</button>
                        {this.state.modifying &&
                            <button className="delete-button app-button" onClick={this.deletePost}>Delete post</button>
                        }
                    </div>
                </form>
            </div>
        );
    }
}

export default withRouter(NewPostForm);
