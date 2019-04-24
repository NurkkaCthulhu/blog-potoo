import React, {Component} from "react";
import './css/Comment_style.css';
import { Link } from "react-router-dom";

class NewComment extends Component {
    constructor(props) {
        super(props);
        this.makeNewComment = this.makeNewComment.bind(this);
        this.state = {comment: ''
                    , tooLong: false}
    }

    handleChange = (event) => {
        const target = event.target;
        let value = target.value;
        let name = target.name;
        let tooLong = value.length > 2000;

        this.setState({
            [name]: value
            , tooLong: tooLong
        });
    };

    handleSubmit = (event) => {
        if(this.state.comment.length <= 0) {
            alert('You can\'t submit an empty comment');
        } else if (this.state.comment.length > 2000) {
            alert('Comment too long!');
        } else {
            this.makeNewComment();
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
                <div>
                    <p className={"charsLeft " + "longpost" + this.state.tooLong}>{this.state.comment.length}/2000</p>
                    <button onClick={this.handleSubmit} className={"commentSubmitButton"}>Submit</button>
                </div>
            </div>
        );
    }
}

export default NewComment;