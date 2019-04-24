import React, {Component} from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import './css/frontpage_style.css';
import PostLoader from './PostLoader';
import NewPostForm from './NewPostForm';
import ErrorPage from './ErrorPage';
import Login from './Login';
import Register from './Register';
import Header from './Header';
import SearchPage from './SearchPage';

function Index() {
    return <PostLoader />;
}

function Error() {
    return <ErrorPage message={'Invalid url 404'}/>
}

function LoginUser() {
    return <Login />
}

function RegisterUser() {
    return <Register />
}

class App extends Component {
    constructor() {
        super();

        let monitorType = window.innerWidth >= 500 ? 'desktop' : 'mobile';
        let loggedIn = (localStorage.getItem('loggedin') === 'true');
        this.state = {monitorType: monitorType, loggedIn: loggedIn}

        window.onresize = this.checkWindowSize.bind(this);
        this.checkLoggedIn = this.checkLoggedIn.bind(this);
    }

    checkLoggedIn(trueOrFalse) {
        this.setState({loggedIn: trueOrFalse});
    }

    checkWindowSize() {
        if (window.innerWidth >= 500) {
            this.setState({monitorType: 'desktop'});
        } else {
            this.setState({monitorType: 'mobile'});
        }
    }

    render() {

        return (
            <Router>
                <div>
                    <Header monitorType={this.state.monitorType} />
                    <div className={"blog-posts " + this.state.monitorType}>
                        <Switch>
                            <Route exact path="/" exact component={Index} />
                            <Route exact path="/search/" component={SearchPage} />
                            <Route exact path="/search/:search" component={SearchPage} />
                            <Route exact path="/blogposts/modifypost/:id" component={NewPostForm} />
                            <Route exact path={"/blogposts/:id"} component={PostLoader}/>
                            <Route exact path="/login" component={LoginUser}/>
                            <Route exact path="/register" component={RegisterUser}/>
                            <Route component={Error}/>
                        </Switch>
                    </div>
                    <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
                </div>
            </Router>
        );
    }
}


export default App;
