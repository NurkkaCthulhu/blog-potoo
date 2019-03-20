import React, {Component} from "react";
import './NewPost_style.css';

class NewPost extends Component {

    newPost = {
        author: 'Potoo mom',
        title: 'Days of Potooing',
        content: 'It is good to be a potoo. I recommend. I feel despair but it is completely ok.',
    };

    constructor() {
        super();
        this.makeNewPost = this.makeNewPost.bind(this);
        this.state = {
            item: this.newPost
        };
    }

    async makeNewPost() {
        const {item} = this.state;

        await fetch('/api/blogposts/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        }).then(() => {
            console.log('This should be posted.');
        });
    }

    render() {
        return (
            <div className = "container">
                <button className="newPost" onClick={this.makeNewPost}>Add New Post</button>
            </div>
        );
    }
}

export default NewPost;
