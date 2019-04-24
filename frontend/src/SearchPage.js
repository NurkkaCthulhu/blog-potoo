import React, {Component} from "react";
import './css/search_style.css';
import { Link } from "react-router-dom";
import SearchBar from "./SearchBar";
import PostLoader from "./PostLoader";
import BlogPost from "./BlogPost";
import ErrorPage from "./ErrorPage";

class SearchPage extends Component {
    constructor(props) {
        super(props);
        console.log(props.match.params.search);
        this.state = {
            postList: []
            , sort: 'newest'
            , fetchedBlogPosts: []};
        this.listAllBlogPosts = this.listAllBlogPosts.bind(this);
        this.writeText = this.writeText.bind(this);
        this.sort = this.sort.bind(this);
    }

    componentDidMount() {
        if (this.props.match.params.search) {
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
        }
    }

    sort(event) {
        event.preventDefault();
        this.setState({sort: event.target.id}, this.componentDidMount)
    }

    writeText(sortedBy) {
        let placeHolder = []

        if (this.state.postList.length > 0) {
            let searchResults = 'Osumia ' + this.state.postList.length + ' kpl';
            placeHolder.push([searchResults, <br/>])

            placeHolder.push(<a id="newest" href="http://" onClick={this.sort}>Viimeksi lisätyt</a>, ' | ');
            placeHolder.push(<a id="priceAsc" href="http://" onClick={this.sort}>Kilohinta nouseva</a>, ' | ');
            placeHolder.push(<a id="priceDesc" href="http://" onClick={this.sort}>Kilohinta laskeva</a>, ' | ');
            placeHolder.push(<a id="availableAsc" href="http://" onClick={this.sort}>Saatavilla nouseva</a>, ' | ');
            placeHolder.push(<a id="availableDesc" href="http://" onClick={this.sort}>Saatavilla laskeva</a>, ' | ');
            placeHolder.push(<a id="nameAsc" href="http://" onClick={this.sort}>A-Ö</a>, ' | ');
            placeHolder.push(<a id="nameDesc" href="http://" onClick={this.sort}>Ö-A</a>);

            switch (sortedBy) {
                case 'newest':
                    placeHolder[1] = <b><a id="newest" href="http://" onClick={this.sort}>Viimeksi lisätyt</a></b>;
                    break;
                case 'priceAsc':
                    placeHolder[3] = <b><a id="priceAsc" href="http://" onClick={this.sort}>Kilohinta nouseva</a></b>;
                    break;
                case 'priceDesc':
                    placeHolder[5] = <b><a id="priceDesc" href="http://" onClick={this.sort}>Kilohinta laskeva</a></b>;
                    break;
                case 'availableAsc':
                    placeHolder[7] = <b><a id="availableAsc" href="http://" onClick={this.sort}>Saatavilla nouseva</a></b>;
                    break;
                case 'availableDesc':
                    placeHolder[9] = <b><a id="availableDesc" href="http://" onClick={this.sort}>Saatavilla laskeva</a></b>;
                    break;
                case 'nameAsc':
                    placeHolder[11] = <b><a id="nameAsc" href="http://" onClick={this.sort}>A-Ö</a></b>;
                    break;
                case 'nameDesc':
                    placeHolder[13] = <b><a id="nameDesc" href="http://" onClick={this.sort}>Ö-A</a></b>;
                    break;
                default:
                    break;
            }
        } else {
            placeHolder.push('Ei osumia tälle hakusanalle.');
        }

        return placeHolder;
    }

    listAllBlogPosts() {
        let helperArray = [];

        if(this.state.fetchedBlogPosts === null) {
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
            <div className="post-list-container">
                <SearchBar/>
                <div className="textContainer">
                    <h3 className="listTitle">Hakutulokset
                        {this.props.match.params.search ?
                                "sanalle"
                            :
                                "tagille"}
                            "{this.props.match.params.search ?
                                this.props.match.params.search
                            :
                                this.props.match.params.tag}"
                    </h3>
                    {this.writeText(this.state.sort)}
                </div>
                {this.state.postList}
            </div>
        )
    }
}

export default SearchPage;