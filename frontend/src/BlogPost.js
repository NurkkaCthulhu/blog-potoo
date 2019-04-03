import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './css/BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        let postUrl = this.props.route + this.props.blogpost.id;
        this.deletePost = this.deletePost.bind(this);
        this.listOfTags = this.listOfTags.bind(this);
        this.state = {
            author: ''
            , title: ''
            , content: ''
            , postDate: ''
            , postTime: ''
            , tags: []
            , postUrl: postUrl
        }
    }

    listOfTags() {
        let tagString = '';

        for (let tagObj of this.props.blogpost.tags) {
            console.log(tagObj);
            tagString = tagString + '#' + tagObj.tagName + ' ';
        }

        return tagString;
    }

    async deletePost() {

        await fetch('/api/blogposts/' + this.props.blogpost.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            this.props.updateLoader();
        });
    }

    render() {
        console.log('BlogPost: ' + this.props.blogpost.id);
        return (
                <div className = "container">

                    <h1><Link to={this.state.postUrl}>{this.props.blogpost.title} </Link> <button className="deletebutton" onClick={this.deletePost}>X</button>
                    <i className='far fa-eye'></i> </h1>
                <h3>{this.props.blogpost.author}</h3>
                <p>Posted: {this.props.blogpost.dateOfCreation} at {this.props.blogpost.timeOfCreation.substring(0,5)}</p>
                <p>{this.props.blogpost.content}</p>
                <p className = "tagsOfPosts">{this.listOfTags()}</p>
            </div>
        );
    }
}

export default BlogPost;
