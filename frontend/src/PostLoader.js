import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import BlogPost from './BlogPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.showOneBlogPost = this.showOneBlogPost.bind(this);
        this.state = {arrayOfBlogPosts: [], route: '/blogposts/'};
    }

    componentDidMount() {
        this.updatePosts();
    }

    updatePosts() {
        fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
    }

    listAllBlogPosts(jsonObject) {
        let helperArray = [];

        for (let obj of jsonObject) {
            helperArray.push(<BlogPost key={obj.id} blogpost={obj} route={this.state.route}
                                       updateLoader={this.updatePosts} showThisPost={this.showOneBlogPost}/>);
        }

        this.setState({arrayOfBlogPosts: helperArray});

        console.log('List all: ' + this.state.arrayOfBlogPosts);
    }

    showOneBlogPost() {
        console.log('This is one post method');
    }

    render() {

        return (
            <div>
                {this.state.arrayOfBlogPosts}
            </div>
        );
    }
}


export default PostLoader;
