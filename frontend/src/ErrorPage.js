import React, {Component} from "react";
import './css/ErrorPage_style.css';

class ErrorPage extends Component {

    render() {
        return (
            <h1>{this.props.message}</h1>
        )
    }
}

export default ErrorPage;
