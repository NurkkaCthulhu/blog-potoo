import React, {Component} from "react";
import './css/Comment_style.css';

class Comment extends Component {
    constructor(props) {
        super(props);
        console.log('propsit commentissa ', props);
    }

    render() {
        return (
            <div className="comment">
                <div className="commentContent">
                    <h3>{this.props.comment.commenter.username} says:</h3>
                    <p>{this.props.comment.content}</p>
                </div>
            </div>
        );
    }
}

export default Comment;