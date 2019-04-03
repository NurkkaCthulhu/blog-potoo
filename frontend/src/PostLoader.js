import React, {Component} from "react";
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
        this.listAllBlogPosts();
        //fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
    }

    listAllBlogPosts() {
        let helperArray = [];

        for (let i = 0; i < 3; i++) {
            let keyId = i + 1;
            helperArray.push(<BlogPost key={keyId} id={keyId} />);
        }

        this.setState({arrayOfBlogPosts: helperArray});

        console.log('List all: ' + this.state.arrayOfBlogPosts);
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
