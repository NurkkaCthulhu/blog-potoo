import React, {Component} from "react";
import './css/BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        this.deletePost = this.deletePost.bind(this);
        this.state = {
            author: ''
            , title: ''
            , content: ''
            , postDate : ''
        }
    }

    componentDidMount() {
        this.setState({'author': this.props.id.author
            , 'title': this.props.id.title
            , 'content' : this.props.id.content
            , 'postDate' : this.props.id.timeOfCreation});
    }

    async deletePost() {

        await fetch('/api/blogposts/' + this.props.id.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            this.props.sendData();
        });
    }

    render() {
        return (
            <div className = "container">

                <h1>{this.state.title} <button className="deletebutton" onClick={this.deletePost}>X</button>
                    <i className='far fa-eye'></i> </h1>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate}</p>
                <p>{this.state.content}</p>
            </div>
        );
    }
}

export default BlogPost;
