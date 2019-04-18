import React, {Component} from "react";
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import './css/frontpage_style.css';

class Header extends Component {
    constructor() {
        super();
        let loggedIn = false;
        if (localStorage.getItem('loggedin') === 'true') {
            loggedIn = true;
        }
        this.state = {loggedIn: loggedIn};

        this.logout = this.logout.bind(this);
    }

    logout() {
        localStorage.removeItem('userId');
        localStorage.removeItem('loggedin');
        localStorage.removeItem('userType');
        this.setState({loggedIn: false});
    }

    render() {
        if (!this.state.loggedIn && localStorage.getItem('loggedin') === 'true') {
            this.setState({loggedIn: true})
        }

        return (
            <div>
                {this.state.loggedIn ?
                <nav className="header">
                    <Link to="/"><i className='fab fa-earlybirds'></i>Blog Potoo</Link>
                    <Link to="/" onClick={this.logout} className="registerlink">Logout</Link>
                    <Link to="/blogposts/modifypost/new" className="newpostlink">Add blog post...</Link>
                </nav>
                :
                 <nav className="header">
                    <Link to="/"><i className='fab fa-earlybirds'></i>Blog Potoo</Link>
                    <Link to="/register" className="registerlink">Register</Link>
                    <Link to="/login" className="loginlink">LogIn</Link>
                </nav>}
            </div>
        );
    }
}

export default Header;