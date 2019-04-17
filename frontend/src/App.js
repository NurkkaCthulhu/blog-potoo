import React from "react";
import { BrowserRouter as Router, Route, Link, Switch } from "react-router-dom";
import './css/frontpage_style.css';
import PostLoader from './PostLoader';
import NewPostForm from './NewPostForm';
import BlogPost from './BlogPost';
import ErrorPage from "./ErrorPage";


function Index() {
    return <PostLoader />;
}

function Error() {
    return <ErrorPage message={'Invalid url 404'}/>
}

function App() {

    return (
        <Router>
            <div>
                <nav className="header">
                    <Link to="/"><i className='fab fa-earlybirds'></i>Blog Potoo</Link>
                    <Link to="/blogposts/modifypost/new" className="newpostlink">Add blog post...</Link>
                </nav>
                <div className="right-float">
                    <p>Archives</p>
                </div>
                <div className="blog-posts">
                    <Switch>
                        <Route exact path="/" exact component={Index} />
                        <Route exact path="/search/:search" component={PostLoader} />
                        <Route exact path="/blogposts/modifypost/:id" component={NewPostForm} />
                        <Route exact path={"/blogposts/:id"} component={BlogPost}/>
                        <Route component={Error}/>
                    </Switch>
                </div>
                <div className="footer"><span>Blog Potoo, the blog of the future (2019)</span></div>
            </div>
        </Router>
    );
}


export default App;
