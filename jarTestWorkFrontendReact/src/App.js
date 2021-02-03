import './App.css';
import Header from './components/Header/Header';
import {BrowserRouter, Route} from "react-router-dom";
import React, {Component} from "react";
import $ from "jquery";
import EditCategories from "./components/EditCategories/EditCategories";
import EditBanners from "./components/EditBanners/EditBanners";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
        this.getCategoryFormData = this.getCategoryFormData.bind(this);
    }

    getCategoryFormData(category,reqid){
        this.state.categoryName = category;
        this.state.categoryReqId = reqid;
        console.log(this.state.categoryName+'   '+this.state.categoryReqId);
    }

    OnClickCreateNewCategory = () => {
        console.log("OnClickCreateNewCategory ");
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/AddCategory',
                contentType: 'application/json; charset=utf-8',
                data:  JSON.stringify({category_name:this.state.categoryName,categoryReqId:this.state.categoryReqId}),
                dataType: 'json',
                async: true,
                cache: false,
                success: function() {
                    console.log("add category query is success")
                    this.setState({
                        isLoaded: true
                    });

                }.bind(this),
                error: function(xhr, status, err) {
                    console.error(this.props.url, status, err.toString());
                }.bind(this)
            });
    }


    ////component={() => <EditBanners updateData={this.updateData} />}

    render(){
        return (
            <BrowserRouter>
                <div className='app-wrapper'>
                    <Header/>
                    <Route path='/CategoryEdit' component={EditCategories}/>
                    <Route path='/BannersEdit' component={EditBanners}/>
                </div>
            </BrowserRouter>
        );
    }
}





