import React, {Component} from "react";
import './css/search_style.css';
import { Link, withRouter } from "react-router-dom";
import SearchBar from "./SearchBar";
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
            , arrayOfBlogPosts: []
        };
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.writeText = this.writeText.bind(this);
        this.sort = this.sort.bind(this);
    }

    componentDidMount() {

        if (this.state.searchWord !== '') {fetch('/api/blogposts/search_all/' + this.state.searchWord)
                                               .then((httpResponse) => httpResponse.json())
                                               .then(this.listAllBlogPosts);}

        /**if (this.props.match.params.search) {
            switch (this.state.sort) {
                case 'newest':
                    fetch('/api/search/' + this.props.match.params.search)
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'priceAsc':
                    fetch('/api/search/' + this.props.match.params.search + '/sortByPriceAsc/true')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'priceDesc':
                    fetch('/api/search/' + this.props.match.params.search + '/sortByPriceAsc/false')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'availableAsc':
                    fetch('/api/search/' + this.props.match.params.search + '/sortByAvailableToAsc/true')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'availableDesc':
                    fetch('/api/search/' + this.props.match.params.search + '/sortByAvailableToAsc/false')
                        .then((httpResponse) => httpResponse.json()).then(this.listAllBlogPosts);
                    break;
                case 'nameAsc': fetch('/api/search/' + this.props.match.params.search + '/sortByNameAsc/true')
                    .then((httpResponse) => httpResponse.json())
                    .then(this.listAllBlogPosts);
                break;
                case 'nameDesc':
                    fetch('/api/search/' + this.props.match.params.search + '/sortByNameAsc/false')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                default:
                    break;
            }
        } else {
            switch (this.state.sort) {
                case 'newest':
                    fetch('/api/products/tag/' + this.props.match.params.tag)
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'priceAsc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByPriceAsc/true')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'priceDesc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByPriceAsc/false')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'availableAsc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByAvailableToAsc/true')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'availableDesc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByAvailableToAsc/false')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'nameAsc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByNameAsc/true')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                case 'nameDesc':
                    fetch('/api/products/tag/' + this.props.match.params.tag + '/sortByNameAsc/false')
                        .then((httpResponse) => httpResponse.json())
                        .then(this.listAllBlogPosts);
                    break;
                default:
                    break;
            }
        }*/
    }

    sort(event) {
        event.preventDefault();
        this.setState({sort: event.target.id}, this.componentDidMount)
    }

    writeText(sortedBy) {
        let placeHolder = []

        if (this.state.arrayOfBlogPosts.length > 0) {
            let searchResults = 'Found ' + this.state.arrayOfBlogPosts.length + ' matches';
            placeHolder.push(<h3 className="listTitle">Search results</h3>);
            placeHolder.push([searchResults, <br/>])

            placeHolder.push(<a id="newest" href="http://" onClick={this.sort}>Newest first</a>, ' | ');
            placeHolder.push(<a id="oldest" href="http://" onClick={this.sort}>Oldest first</a>, ' | ');
            placeHolder.push(<a id="titleAlphAsc" href="http://" onClick={this.sort}>Title A-Z</a>, ' | ');
            placeHolder.push(<a id="titleAlphDesc" href="http://" onClick={this.sort}>Title Z-A</a>, ' | ');
            placeHolder.push(<a id="authorAlphAsc" href="http://" onClick={this.sort}>Author A-Z</a>, ' | ');
            placeHolder.push(<a id="authorAlphDesc" href="http://" onClick={this.sort}>Author Z-A</a>, ' | ');
            placeHolder.push(<a id="lenghtDesc" href="http://" onClick={this.sort}>Longest first</a>, ' | ');
            placeHolder.push(<a id="lenghtAsc" href="http://" onClick={this.sort}>Shortest first</a>);

            switch (sortedBy) {
                case 'newest':
                    placeHolder[2] = <b><a id="newest" href="http://" onClick={this.sort}>Newest first</a></b>;
                    break;
                case 'oldest':
                    placeHolder[4] = <b><a id="priceAsc" href="http://" onClick={this.sort}>Oldest first</a></b>;
                    break;
                case 'titleAlphAsc':
                    placeHolder[6] = <b><a id="priceDesc" href="http://" onClick={this.sort}>Title A-Z</a></b>;
                    break;
                case 'titleAlphDesc':
                    placeHolder[8] = <b><a id="availableAsc" href="http://" onClick={this.sort}>Title Z-A</a></b>;
                    break;
                case 'authorAlphAsc':
                    placeHolder[10] = <b><a id="availableDesc" href="http://" onClick={this.sort}>Author A-Z</a></b>;
                    break;
                case 'authorAlphDesc':
                    placeHolder[12] = <b><a id="nameAsc" href="http://" onClick={this.sort}>Author Z-A</a></b>;
                    break;
                case 'lenghtDesc':
                    placeHolder[14] = <b><a id="nameDesc" href="http://" onClick={this.sort}>Longest first</a></b>;
                    break;
                case 'lenghtAsc':
                    placeHolder[16] = <b><a id="nameDesc" href="http://" onClick={this.sort}>Shortest first</a></b>;
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