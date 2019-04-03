import React, {Component} from "react";
import BlogPost from './BlogPost';

class PostLoader extends Component {
    constructor(props) {
        super(props);
        var search = '';
        if (this.props.match === undefined) {
            search = '/';
        } else {
            console.log("help")
            search = '/search_all/' + this.props.match.params.search;
        }

        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.state = {search: search, arrayOfBlogPosts: [], blogPostIds: []};
    }

    componentDidMount() {
        let fetchURL = '/api/blogposts' + this.state.search;
        fetch(fetchURL).then((httpResponse) => httpResponse.json()).then((json) => this.setState({blogPostIds: json})).then(() => this.updatePosts());
    }

    updatePosts() {
        this.listAllBlogPosts();
        //fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
    }

    listAllBlogPosts() {
        let helperArray = [];

        console.log(this.state.blogPostIds);

        for (let id of this.state.blogPostIds) {
            helperArray.push(<BlogPost key={id} id={id} />);
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
