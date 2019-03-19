import React, {Component} from "react";
import './frontpage_style.css';

class BlogPost extends Component {

    constructor(props) {
        super(props);
        this.state = {
            author: ''
            , title: ''
            , content: ''
            , postDate : ''
        }
    }

    componentDidMount() {
        fetch('/blogposts/' + this.props.id).then(httpResp => httpResp.json())
            .then(blogpost => {
                this.setState({'author': blogpost.author
                                , 'title': blogpost.title
                                , 'content' : blogpost.content
                                , 'postDate' : blogpost.timeOfCreation});
            });
    }

    render() {
        return (
            <div className = "blogpost">
                <h1>{this.state.author}</h1>
                <p>Posted: {this.state.postDate}</p>
            </div>
        );
    }
}


export default BlogPost;
