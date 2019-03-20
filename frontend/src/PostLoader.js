import React, {Component} from "react";
import BlogPost from './BlogPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.state = {arrayOfBlogPosts: []}
    }

    listAllBlogPosts(jsonObject) {
        console.log(jsonObject);

        let helperArray = [];

        for (let obj of jsonObject) {
            helperArray.push(<BlogPost id={obj.id}/>);
        }

        this.setState({arrayOfBlogPosts: helperArray});
    }

    render() {
        fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);

        return (
            <div>
                {this.state.arrayOfBlogPosts}
            </div>
        );
    }
}


export default PostLoader;
