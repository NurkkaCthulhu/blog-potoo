import React, {Component} from "react";
import './BlogPost_style.css';

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
                <button onClick={this.makeNewPost}>New Post</button>
            </div>
        );
    }
}

export default NewPost;
