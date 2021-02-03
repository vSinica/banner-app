import React from 'react'; 
import s from './Header.module.css';
import {NavLink} from "react-router-dom";

const Header = () =>{
    return <header className={s.header}>
        <div className={`${s.item} ${s.active}`}>
            <NavLink to="/BannersEdit" activeClassName={s.activeLink}>Banners</NavLink>
        </div>
        <div className={`${s.item} ${s.active}`}>
            <NavLink to="/CategoryEdit" activeClassName={s.activeLink}>Category</NavLink>
        </div>
    </header>
}

export default Header;