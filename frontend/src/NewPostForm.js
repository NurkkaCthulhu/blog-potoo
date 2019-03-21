import React, {Component} from "react";
import './NewPostForm_style.css';

class NewPostForm extends Component {

    newPost = {
        author: 'Potoo mom',
        title: 'Days of Potooing',
        content: 'It is good to be a potoo. I recommend. I feel despair but it is completely ok.',
    };

    constructor() {
        super();
        this.state = {
            item: this.newPost
        };
    }

    render() {
        return (
            <div className = "container">
                <h1>Make a new post</h1>
            </div>
        );
    }
}

export default NewPostForm;
