import React, {Component} from "react";
import './css/Comment_style.css';
import { Link } from "react-router-dom";

class NewComment extends Component {
    constructor(props) {
        super(props);
        this.makeNewComment = this.makeNewComment.bind(this);
        this.state = {comment: ''}
    }

    handleChange = (event) => {
        const target = event.target;
        let value = target.value;
        let name = target.name;

        this.setState({
            [name]: value
        });
    };

    handleSubmit = (event) => {
        if(this.state.comment.length > 0) {
            this.makeNewComment();
        } else {
            alert('You can\'t submit an empty comment');
        }
        event.preventDefault();
    };

    async makeNewComment() {
        const newComment = {
            userId: localStorage.getItem('userId')
            , content: this.state.comment
        };
        console.log('uus okommentti', newComment);

        await fetch('/api/blogposts/' + this.props.postId + '/comments', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newComment),
        }).then(() => console.log('done'));
    }

    render() {
        return (
            <div className="newcomment">
                <textarea placeholder="Write comment" name="comment" className="newComment" onChange={this.handleChange} />
                <div className={"floatRight"}>
                    <p>{this.state.comment.length}/2000</p>
                    <button onClick={this.handleSubmit}>Submit</button>
                </div>
            </div>
        );
    }
}

export default NewComment;