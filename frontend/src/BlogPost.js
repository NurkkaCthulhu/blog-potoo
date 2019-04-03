import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './css/BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        console.log('Propsit postis ')
        console.log(props)
        var id = this.props.id;
        if (this.props.match === undefined) {
            id  = props.id;
        } else {
            id  = this.props.match.params.id;
        }
        let modifyUrl = 'modifyPost/' + id;
        this.deletePost = this.deletePost.bind(this);
        this.listOfTags = this.listOfTags.bind(this);

        this.state = {
            id: id
            , author: ''
            , title: ''
            , content: ''
            , postDate: '0000-01-01'
            , postTime: '00:00:00'
            , tags: []
            , postUrl: ''
            , modifyUrl: modifyUrl
        }
    }

    componentDidMount() {
        console.log('Console mount ' + this.state);
        console.log(this.state);
        fetch('/api/blogposts/' + this.state.id).then((httpResponse) => httpResponse.json())
            .then((blogpost) => {
                let postUrl = '/blogposts/' + this.state.id;
                this.setState({
                    author: blogpost.author
                    , title: blogpost.title
                    , content: blogpost.content
                    , postDate: blogpost.dateOfCreation
                    , postTime: blogpost.timeOfCreation
                    , tags: blogpost.tags
                    , postUrl: postUrl
                });
                //this.setState({blogpost: blogObject});
            }
        );
    }

    listOfTags() {
        let tagString = '';

        for (let tagObj of this.state.tags) {
            console.log(tagObj);
            tagString = tagString + '#' + tagObj.tagName + ' ';
        }

        return tagString;
    }

    async deletePost() {

        await fetch('/api/blogposts/' + this.state.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            //this.props.updateLoader();
        });
    }

    render() {
        console.log('BlogPost: ' + this.state.id);
        console.log('STATE: ');
        console.log(this.state);
        return (
                <div className = "container">

                    <h1><Link to={this.state.postUrl}>{this.state.title} </Link>
                    </h1>
                    <div className="postIcons">
                        <button className="deletebutton" onClick={this.deletePost}>X</button>
                        <Link to={this.state.modifyUrl}><button className="modifybutton"><i className='fas fa-pen'></i></button></Link>
                        <i className='far fa-eye-slash'></i>
                    </div>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate} at {this.state.postTime.substring(0,5)}</p>
                <p>{this.state.content}</p>
                <p className = "tagsOfPosts">{this.listOfTags()}</p>
            </div>
        );
    }
}

export default BlogPost;
