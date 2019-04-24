import React, {Component} from "react";
import './css/Comment_style.css';

class Comment extends Component {
    constructor(props) {
        super(props);
        //console.log('props in comment ', props);
    }

    render() {
        return (
            <div className="comment">
                <div className="commentContent">
                    <h3>{this.props.comment.commenter.username}</h3>
                    <small>{this.props.comment.dateOfComment + " at " + this.props.comment.timeOfComment}</small>
                    <p>{this.props.comment.content}</p>
                </div>
            </div>
        );
    }
}

export default Comment;