import React, {Component} from "react";
import { Link} from "react-router-dom";
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
                    , postsFrom: 0
                    , postsTo: 5
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

    loadOlderPosts = () => {
        let newPostsFrom = this.state.postsFrom + 5;
        let newPostsTo = this.state.postsTo + 5;
        this.setState({postsFrom: newPostsFrom
                        , postsTo: newPostsTo});
    }

    loadNewerPosts = () => {
        let newPostsFrom = this.state.postsFrom - 5;
        let newPostsTo = this.state.postsTo - 5;
        this.setState({postsFrom: newPostsFrom
            , postsTo: newPostsTo});
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
                        this.state.arrayOfBlogPosts.length > 5 ?
                            <div>
                                {this.state.arrayOfBlogPosts.slice(this.state.postsFrom, this.state.postsTo)}
                                {(this.state.postsFrom + 5) < this.state.arrayOfBlogPosts.length &&
                                    <button className={"postsSpan"} onClick={this.loadOlderPosts}><i className='fas fa-arrow-alt-circle-left'></i>Older posts &nbsp;</button>

                                }
                                {(this.state.postsFrom-5) >= 0 &&
                                    <button className={"postsSpan"} onClick={this.loadNewerPosts}><i className='fas fa-arrow-alt-circle-right'></i>Newer posts &nbsp;</button>
                                }
                            </div>
                            :
                            this.state.arrayOfBlogPosts
                }
            </div>
        );
    }
}


export default PostLoader;
