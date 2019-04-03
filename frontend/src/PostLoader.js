import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import BlogPost from './BlogPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.state = {arrayOfBlogPosts: []};
    }

    componentDidMount() {
        this.updatePosts();
    }

    updatePosts() {
        this.setState({isFetching: true});
        this.listAllBlogPosts();
        //fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
    }

    listAllBlogPosts() {
        let helperArray = [];

        for (let i = 0; i < 3; i++) {
            let keyId = i + 1;
            helperArray.push(<BlogPost key={keyId} id={keyId} />);
        }

        this.setState({arrayOfBlogPosts: helperArray, isFetching: false});

        console.log('List all: ' + this.state.arrayOfBlogPosts);
    }

    render() {
        const {isFetching} = this.state;
        console.log('Fetsaaminen: ' + isFetching);
        if(isFetching || isFetching === undefined) {
            return <p>Loading.....</p>;
        }
        return (
            <div>
                {this.state.arrayOfBlogPosts}
            </div>
        );
    }
}


export default PostLoader;
