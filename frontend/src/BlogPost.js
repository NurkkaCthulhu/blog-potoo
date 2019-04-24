import React, {Component} from "react";
import { Link } from "react-router-dom";
import './css/BlogPost_style.css';
import Comment from "./Comment";
import NewComment from "./NewComment";
import ErrorPage from "./ErrorPage";

class BlogPost extends Component {

    constructor(props) {
        super(props);

        let id = this.props.post.id;

        let modifyUrl = '/blogposts/modifypost/' + id;
        this.listOfTags = this.listOfTags.bind(this);
        this.makeSeen = this.makeSeen.bind(this);
        this.listAllComments = this.listAllComments.bind(this);
        this.updateComments = this.updateComments.bind(this);

        let content = this.props.post.content;
        let cutContent = false;
        let frontpagePostLength = 500;
        // Show only first 500 chars of the post (includes rich text styling)
        if (content.length > frontpagePostLength && !this.props.ownPage) {
            content = content.substr(0, 500);
            content += '...';
            cutContent = true;
        }

        this.state = {
            id: id
            , author: this.props.post.author.username
            , title: this.props.post.title
            , content: content
            , cutContent: cutContent
            , postDate: this.props.post.dateOfCreation
            , postTime: this.props.post.timeOfCreation
            , tags: this.props.post.tags
            , postUrl: `/blogposts/${this.props.post.id}`
            , modifyUrl: modifyUrl
            , seen: false
            , fetchedComments: []
            , arrayOfComments: []
        }
    }

    componentDidMount() {
        if(localStorage.getItem('loggedin') === 'true') {
            fetch('/api/blogposts/' + this.state.id + '/viewAndLike/' + localStorage.getItem('userId'))
                .then((httpResp) => httpResp.json())
                .then((json) => {
                    if(json) {
                        if (this.props.ownPage && !json.viewed) {
                            this.makeSeen();
                        } else {
                            this.setState({seen: json.viewed})
                        }
                    }
                });
        }

        if(this.props.ownPage) {
            this.updateComments();
        }
    }

    updateComments() {
        fetch('/api/blogposts/' + this.state.id + '/comments')
            .then((httpResponse) => httpResponse.json())
            .then((json) => this.setState({fetchedComments: json}))
            .then(() => this.listAllComments());
    }

    listAllComments() {
        let helperArray = [];

        if(this.state.fetchedComments === null) {
            helperArray.push(<ErrorPage key={1} message={"Comment found 404"}/>)
        } else if(this.state.fetchedComments.length === undefined) {
            helperArray.push(<Comment key={this.state.fetchedComments.id} comment={this.state.fetchedComments} updateComments={this.updateComments}/>);
        } else {
            for (let comment of this.state.fetchedComments) {
                helperArray.push(<Comment key={comment.id} comment={comment} updateComments={this.updateComments}/>);
            }
        }
        helperArray.sort(function(a, b) {return a.key - b.key});
        this.setState({arrayOfComments: helperArray});
    }

    makeSeen() {
        let fetchUrl = '/api/blogposts/' + this.state.id + '/toggleView/' + localStorage.getItem('userId');
        fetch(fetchUrl, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: ''})
            .then(() => {
                    let seen = !this.state.seen;
                    this.setState({seen: seen});
                }
            );
    }

    listOfTags() {
        let tagString = '';

        for (let tagObj of this.state.tags) {
            tagString = tagString + '#' + tagObj.tagName + ' ';
        }

        return tagString;
    }

    render() {

        return (
            <div>
                <div className="postheader">
                    <Link to={this.state.postUrl}><h1 className={"blogtitle"}>{this.state.title}</h1></Link>
                </div>
                <div className="postIcons">
                    {localStorage.getItem('userType') === 'ADMIN' &&
                        <Link to={this.state.modifyUrl}>
                            <button className="modifybutton"><i className='fas fa-pen'></i></button>
                        </Link>
                    }
                    {localStorage.getItem('loggedin') === 'true' &&
                        <i className={this.state.seen ? 'far fa-eye' : 'far fa-eye-slash'} onClick={this.makeSeen}></i>
                    }

                </div>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate} at {this.state.postTime.substring(0, 5)}</p>
                <div dangerouslySetInnerHTML={{__html: this.state.content}}></div>
                {this.state.cutContent &&
                    <Link to={this.state.postUrl}><p className="readmore">Read more</p></Link>
                }
                <p className="tagsOfPosts">{this.listOfTags()}</p>

                {this.props.ownPage &&
                    <div className="blogpost_comments">
                        { localStorage.getItem('loggedin') === 'true' &&
                            <NewComment postId={this.state.id} updateComments={this.updateComments}/>
                        }
                        <br/>
                        {this.state.arrayOfComments}
                    </div>
                }
            </div>

        );
    }
}

export default BlogPost;
