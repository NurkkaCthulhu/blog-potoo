import React, {Component} from "react";
import './css/search_style.css';
import { Link } from "react-router-dom";

class Comment extends Component {
    constructor(props) {
        super(props);
        console.log('propsit commentissa ', props);
    }

    render() {
        return (
            <div className="comment">
                <h1>{this.props.comment.content}</h1>
            </div>
        );
    }
}

export default Comment;