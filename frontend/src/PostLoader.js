import React, {Component} from "react";
import BlogPost from './BlogPost';
import NewPost from './NewPost';

class PostLoader extends Component {

    constructor() {
        super();
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
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
            helperArray.push(<BlogPost key={obj.id} id={obj} sendData={this.updatePosts}/>);
        }

        this.setState({arrayOfBlogPosts: helperArray});

        console.log(this.state.arrayOfBlogPosts);
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
