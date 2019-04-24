import React, {Component} from "react";
import './css/search_style.css';
import { Link } from "react-router-dom";

class NewComment extends Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.state = {searchWord: ''}
    }

    handleChange(event) {
        let value = event.target.value;
        this.setState({searchWord: value});
        event.preventDefault();
    }

    handleSubmit(event) {
        console.log('lololo');
        event.preventDefault();
    }

    render() {
        return (
            <div className="newcomment">
                <input type="textarea" placeholder="Write comment" name="comment" onChange={this.handleChange} />
                <button type="submit">Submit</button>
            </div>
        );
    }
}

export default NewComment;