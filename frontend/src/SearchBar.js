import React, {Component} from "react";
import './css/search_style.css';
import { Link } from "react-router-dom";

class SearchBar extends Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = {searchWord: ''}
    }

    handleChange(event) {
        let value = event.target.value;
        this.setState({searchWord: value});
    }

    render() {
        return (<div className="search">
            <input type="text" placeholder="Search titles, tags or authors..." name="searchBar" onChange={this.handleChange} />
            <Link to={'/search/' + this.state.searchWord}><button type="submit"><i className='fas fa-search'></i></button></Link>
        </div>);
    }
}

export default SearchBar;