import React, {Component} from "react";
import './css/Comment_style.css';
import {Link} from "react-router-dom";

class Comment extends Component {
    constructor(props) {
        super(props);
        //console.log('props in comment ', props);
    }


    render() {
        return (
            <div className="comment">
                <div className="commentContent">
                    {localStorage.getItem('userType') === 'ADMIN' &&
                    <Link to="/">
                        <button className="deletebutton" onClick={this.deleteComment}><i className='fas fa-trash-alt'></i></button>
                    </Link>
                    }
                    <h3>{this.props.comment.commenter.username}</h3>
                    <small>{this.props.comment.dateOfComment + " at " + this.props.comment.timeOfComment}</small>
                    <p>{this.props.comment.content}</p>
                </div>
            </div>
        );
    }
}

export default Comment;