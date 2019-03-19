import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import './frontpage_style.css';

class BlogPost extends Component {

    componentDidMount() {
        fetch('/blogposts').then(httpResp => httpResp.json())
            .then(blogposts => {
               console.log(blogposts);
            });
    }

    render() {
        return (
            <h1>Blogipostaus</h1>
        );
    }
}


export default BlogPost;
