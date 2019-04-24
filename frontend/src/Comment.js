import React, {Component} from "react";
import './css/search_style.css';
import { Link } from "react-router-dom";

class Comment extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="comment">
                <h1>Comment :D</h1>
            </div>
        );
    }
}

export default Comment;