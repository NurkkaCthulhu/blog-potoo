import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import BlogPost from './BlogPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.state = {arrayOfBlogPosts: []
                        , route: '/blogposts/'
                        , isFetching: true};
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
                                       updateLoader={this.updatePosts} />);
        }

        this.setState({arrayOfBlogPosts: helperArray, isFetching: false});

        console.log('List all: ' + this.state.arrayOfBlogPosts);
    }

    render() {
        const {isFetching} = this.state;
        console.log('Fetsaaminen: ' + isFetching)
        if(this.state.isFetching) {
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
