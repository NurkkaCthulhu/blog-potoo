import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import './css/NewPostForm_style.css';

class NewPostForm extends Component {

    constructor(props) {
        super(props);

        this.makeNewPost = this.makeNewPost.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            author: ''
            , title: ''
            , content: ''
        };
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(event) {
        this.makeNewPost();
        event.preventDefault();
    }

    async makeNewPost() {
        const newPost = {
            author: this.state.author,
            title: this.state.title,
            content: this.state.content,
        };

        await fetch('/api/blogposts/', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newPost),
        }).then(() => {
            this.props.history.push("/")
        })
    }

    render() {
        return (
            <div className = "container">
                <h1>Make a new post</h1>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Author
                        <br />
                        <input type="text" value={this.state.author} onChange={this.handleChange} name="author"/>
                    </label>
                    <br />
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

export default withRouter(NewPostForm);
