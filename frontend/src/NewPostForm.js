import React, {Component} from "react";
import './css/NewPostForm_style.css';

class NewPostForm extends Component {

    newPost = {
        author: 'Potoo mom',
        title: '',
        content: '',
    };

    constructor(props) {
        super(props);

        this.makeNewPost = this.makeNewPost.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            item: this.newPost
        };
    }

    handleChange(event) {

    }

    handleSubmit(event) {
        alert('An essay was submitted: ' + this.state.content + ' Title: ' + this.state.title);
        event.preventDefault();
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
            this.props.sendData();
        });
    }

    render() {
        return (
            <div className = "container">
                <h1>Make a new post</h1>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Title
                        <br />
                        <input type="text" value={this.state.title} onChange={this.handleChange} name="title"/>
                    </label>
                    <br />
                    <label>
                        Content
                        <br />
                        <textarea value={this.state.content} onChange={this.handleChange} name="content"/>
                    </label>
                    <br />
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default NewPostForm;
