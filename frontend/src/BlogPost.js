import React, {Component} from "react";
import './BlogPost_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        this.deletePost = this.deletePost.bind(this);
        console.log(this.props.id)
        this.state = {
            author: ''
            , title: ''
            , content: ''
            , postDate : ''
        }
    }

    componentDidMount() {
        fetch('/api/blogposts/' + this.props.id).then(httpResp => httpResp.json())
            .then(blogpost => {
                this.setState({'author': blogpost.author
                                , 'title': blogpost.title
                                , 'content' : blogpost.content
                                , 'postDate' : blogpost.timeOfCreation});
                this.props.sendData();
            });
    }

    async deletePost() {
        console.log(this.props.id);

        await fetch('/api/blogposts/' + this.props.id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            console.log('This should be deleted.');
            this.props.sendData();
        });
    }

    render() {
        return (
            <div className = "container">

                <h1>{this.state.title} <button className="deletebutton" onClick={this.deletePost}>X</button> </h1>
                <h3>{this.state.author}</h3>
                <p>Posted: {this.state.postDate}</p>
                <p>{this.state.content}</p>
            </div>
        );
    }
}

export default BlogPost;
