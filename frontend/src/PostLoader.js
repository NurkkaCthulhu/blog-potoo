import React, {Component} from "react";
import BlogPost from './BlogPost';
import nopostimg from './img/noposts.png';
import ErrorPage from "./ErrorPage";

class PostLoader extends Component {
    constructor(props) {
        super(props);
        var fetchUrl = '';

        if (this.props.match === undefined) {
            fetchUrl = '/';
        } else if (this.props.match.params.search) {
            fetchUrl = '/search_all/' + this.props.match.params.search;
        } else if (!isNaN(this.props.match.params.id)) {
            fetchUrl = '/' + this.props.match.params.id;
        }

        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.updatePosts = this.updatePosts.bind(this);
        this.state = {fetchUrl: fetchUrl
                    , arrayOfBlogPosts: []
                    , fetchedBlogPosts: []
                    , isFetching: false
                    };
    }

    componentDidMount() {
        this.setState({isFetching: true});
        let fetchURL = '/api/blogposts' + this.state.fetchUrl;
        fetch(fetchURL).then((httpResponse) => httpResponse.json()).then((json) => this.setState({fetchedBlogPosts: json})).then(() => this.updatePosts());
    }

    updatePosts() {
        this.setState({isFetching: false});
        this.listAllBlogPosts();
        //fetch('/api/blogposts/').then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
    }

    listAllBlogPosts() {
        let helperArray = [];

        if(this.state.fetchedBlogPosts.status) {
            helperArray.push(<ErrorPage key={1} message={"Blog post not found 404"}/>)
        } else if(this.state.fetchedBlogPosts.length === undefined) {
            helperArray.push(<BlogPost key={this.state.fetchedBlogPosts.id} post={this.state.fetchedBlogPosts} ownPage={true} />);
        } else {
            for (let post of this.state.fetchedBlogPosts) {
                helperArray.push(<BlogPost key={post.id} post={post} ownPage={false}/>);
            }
        }
        this.setState({arrayOfBlogPosts: helperArray});

    }

    render() {
        return (
            <div>
                {this.state.isFetching ?
                    <p>Loading....</p>
                    :
                    this.state.arrayOfBlogPosts.length <= 0 ?
                    <div>
                        <h1 className={"newpostTitle"}>No blog posts :'(</h1>
                        <img className={"nopostsimg"} src={nopostimg} alt={"Crying potoo"}></img>
                    </div>
                    :
                    this.state.arrayOfBlogPosts
                }
            </div>
        );
    }
}


export default PostLoader;
