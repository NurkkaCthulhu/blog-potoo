import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import './css/frontpage_style.css';
import PostLoader from './PostLoader';
import NewPostForm from './NewPostForm';
import BlogPost from './BlogPost';
import ErrorPage from "./ErrorPage";
import Login from "./Login";
import Register from "./Register";

class Header extends Component {
    render() {
        return (
            <nav className="header">
                <Link to="/"><i className='fab fa-earlybirds'></i>Blog Potoo</Link>
                <Link to="/register" className="registerlink">Register</Link>
                <Link to="/login" className="loginlink">LogIn</Link>
                <Link to="/blogposts/modifypost/new" className="newpostlink">Add blog post...</Link>
            </nav>
        );
    }
}

export default Header;