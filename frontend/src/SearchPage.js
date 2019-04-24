import React, {Component} from "react";
import './css/search_style.css';
import { Link, withRouter } from "react-router-dom";
import PostLoader from "./PostLoader";
import BlogPost from "./BlogPost";
import ErrorPage from "./ErrorPage";
import nopostimg from './img/noposts.png';

class SearchPage extends Component {
    constructor(props) {
        super(props);
        let searchWord;
        props.match.params.search ? searchWord = props.match.params.search : searchWord = '';
        this.state = {
            searchWord: searchWord
            , sort: 'newest'
            , postLoader: <PostLoader searchURL={'/search_all/' + searchWord}/>
            , arrayOfBlogPostInformation: []
            , arrayOfBlogPosts: []
        };
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.listAllBlogPostInfo = this.listAllBlogPostInfo.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.writeText = this.writeText.bind(this);
        this.setSort = this.setSort.bind(this);
        this.sort = this.sort.bind(this);
    }

    componentDidMount() {
        if (this.state.searchWord !== '') {
            fetch('/api/blogposts/search_all/' + this.state.searchWord)
               .then((httpResponse) => httpResponse.json())
               .then(this.listAllBlogPostInfo);
        }
    }

    setSort(event) {
        event.preventDefault();
        this.setState({sort: event.target.id}, this.sort)
    }

    sort () {
        switch (this.state.sort) {
            case 'newest':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return b.id - a.id;});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'oldest':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return a.id - b.id;});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'titleAlphAsc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return a.title.localeCompare(b.title);});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'titleAlphDesc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return b.title.localeCompare(a.title);});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'authorAlphAsc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return a.author.username.localeCompare(b.author.username);});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'authorAlphDesc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return b.author.username.localeCompare(a.author.username);});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'lenghtDesc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return b.content.length - a.content.length;});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            case 'lenghtAsc':
                this.state.arrayOfBlogPostInformation.sort(function(a,b) {return a.content.length - b.content.length;});
                this.listAllBlogPosts(this.state.arrayOfBlogPostInformation);
                break;
            default:
                break;
        }
    }

    writeText(sortedBy) {
        let placeHolder = []

        if (this.state.arrayOfBlogPosts.length > 0) {
            let searchResults = 'Found ' + this.state.arrayOfBlogPosts.length + ' matches';
            placeHolder.push(<h3 className="listTitle">Search results</h3>);
            placeHolder.push([searchResults, <br/>])

            placeHolder.push(<a id="newest" href="http://" onClick={this.setSort}>Newest first</a>, ' | ');
            placeHolder.push(<a id="oldest" href="http://" onClick={this.setSort}>Oldest first</a>, ' | ');
            placeHolder.push(<a id="titleAlphAsc" href="http://" onClick={this.setSort}>Title A-Z</a>, ' | ');
            placeHolder.push(<a id="titleAlphDesc" href="http://" onClick={this.setSort}>Title Z-A</a>, ' | ');
            placeHolder.push(<a id="authorAlphAsc" href="http://" onClick={this.setSort}>Author A-Z</a>, ' | ');
            placeHolder.push(<a id="authorAlphDesc" href="http://" onClick={this.setSort}>Author Z-A</a>, ' | ');
            placeHolder.push(<a id="lenghtDesc" href="http://" onClick={this.setSort}>Longest first</a>, ' | ');
            placeHolder.push(<a id="lenghtAsc" href="http://" onClick={this.setSort}>Shortest first</a>);

            switch (sortedBy) {
                case 'newest':
                    placeHolder[2] = <b><a id="newest" href="http://" onClick={this.setSort}>Newest first</a></b>;
                    break;
                case 'oldest':
                    placeHolder[4] = <b><a id="priceAsc" href="http://" onClick={this.setSort}>Oldest first</a></b>;
                    break;
                case 'titleAlphAsc':
                    placeHolder[6] = <b><a id="priceDesc" href="http://" onClick={this.setSort}>Title A-Z</a></b>;
                    break;
                case 'titleAlphDesc':
                    placeHolder[8] = <b><a id="availableAsc" href="http://" onClick={this.setSort}>Title Z-A</a></b>;
                    break;
                case 'authorAlphAsc':
                    placeHolder[10] = <b><a id="availableDesc" href="http://" onClick={this.setSort}>Author A-Z</a></b>;
                    break;
                case 'authorAlphDesc':
                    placeHolder[12] = <b><a id="nameAsc" href="http://" onClick={this.setSort}>Author Z-A</a></b>;
                    break;
                case 'lenghtDesc':
                    placeHolder[14] = <b><a id="nameDesc" href="http://" onClick={this.setSort}>Longest first</a></b>;
                    break;
                case 'lenghtAsc':
                    placeHolder[16] = <b><a id="nameDesc" href="http://" onClick={this.setSort}>Shortest first</a></b>;
                    break;
                default:
                    break;
            }
        } else {
            if (this.state.searchWord !== '') {
                placeHolder.push(<h1 className={"newpostTitle"}>No blog posts found :'(</h1>);
                placeHolder.push(<img className={"nopostsimg"} src={nopostimg} alt={"Crying potoo"}></img>);
            }
        }

        return placeHolder;
    }

    listAllBlogPostInfo(blogposts) {
        let helperArray = [];

        for (let post of blogposts) {
            helperArray.push(post);
        }

        this.setState({arrayOfBlogPostInformation: helperArray}, this.sort);
    }

    listAllBlogPosts(blogposts) {
        let helperArray = [];

        if (blogposts.length === 0 || blogposts === null) {

        } else {
            for (let post of blogposts) {
                helperArray.push(<BlogPost key={post.id} post={post} ownPage={false}/>);
            }
        }
        this.setState({arrayOfBlogPosts: helperArray});
    }

    handleChange(event) {
        this.setState({searchWord: event.target.value});
    }

    doSearch(event) {
        console.log("search:" + this.state.searchWord)
        this.props.history.push('/search/' + this.state.searchWord);
        this.componentDidMount();
    }

    render() {
        return (
            <div className="post-list-container">
                <div className="search">
                    <input type="text" className="searchInput" placeholder="Search titles, tags or authors..." name="searchInput" onChange={this.handleChange} />
                    <button type="submit" onClick={this.doSearch} className="searchButton"><i className='fas fa-search'></i></button>
                </div>
                <div className="textContainer">
                    {this.writeText(this.state.sort)}
                </div>
                {this.state.arrayOfBlogPosts}
            </div>
        )
    }
}

export default withRouter(SearchPage);