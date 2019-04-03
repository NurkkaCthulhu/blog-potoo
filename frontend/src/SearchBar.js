import React, {Component} from "react";
import './css/search_style.css';

class SearchBar extends Component {
    constructor() {
        super();
    }

    render() {
        return (<div className="search">
            <input type="text" placeholder="Search titles, tags or authors..." name="searchBar" />
            <button type="submit"><i className='fas fa-search'></i></button>
        </div>);
    }
}

export default SearchBar;