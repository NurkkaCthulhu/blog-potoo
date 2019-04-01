import React, {Component} from "react";
import BlogPost from './BlogPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.showOneBlogPost = this.showOneBlogPost.bind(this);
        this.state = {arrayOfBlogPosts: []}
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
            helperArray.push(<BlogPost key={obj.id} blogpost={obj} updateLoader={this.updatePosts}/>);
        }

        this.setState({arrayOfBlogPosts: helperArray});

        console.log(this.state.arrayOfBlogPosts);
    }

    showOneBlogPost(obj) {
        let helperArray = [];
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
