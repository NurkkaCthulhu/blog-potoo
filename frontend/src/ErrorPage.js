import React, {Component} from "react";
import './css/ErrorPage_style.css';

class ErrorPage extends Component {

    render() {
        return (
            <h1 className={"errorTitle"}>{this.props.message}</h1>
        )
    }
}

export default ErrorPage;
