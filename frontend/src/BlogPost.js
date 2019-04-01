import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './css/BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        let postUrl = this.props.route + this.props.blogpost.id;
        this.deletePost = this.deletePost.bind(this);
        this.state = {
            author: ''
            , title: ''
            , content: ''
            , postDate: ''
            , postUrl: postUrl
        }
    }

    componentDidMount() {
        this.setState({'author': this.props.blogpost.author
            , 'title': this.props.blogpost.title
            , 'content' : this.props.blogpost.content
            , 'postDate' : this.props.blogpost.timeOfCreation});
        this.props.showThisPost();
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
        console.log('BlogPost: ' + this.props.blogpost);
        return (
                <div className = "container">

                    <h1><Link to={this.state.postUrl}>{this.state.title} </Link> <button className="deletebutton" onClick={this.deletePost}>X</button>
                    <i className='far fa-eye'></i> </h1>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate}</p>
                <p>{this.state.content}</p>
            </div>
        );
    }
}

export default BlogPost;
