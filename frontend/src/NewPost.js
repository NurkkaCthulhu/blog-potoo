import React, {Component} from "react";
import './NewPost_style.css';

class NewPost extends Component {

    constructor() {
        super();
        this.makeNewPost = this.makeNewPost.bind(this);
    }

    makeNewPost() {

    }

    render() {
        return (
            <div className = "container">
                <button className="newPost" onClick={this.makeNewPost}>New Post</button>
            </div>
        );
    }
}

export default NewPost;
