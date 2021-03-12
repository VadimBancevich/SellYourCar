import {Field} from "redux-form";
import RenderTextFiled from "../../../FormElements/common/RenderTextField";
import {Link} from "react-router-dom";
import {locales} from "../../../../locales";
import React from "react";
import CircularProgress from "@material-ui/core/CircularProgress";
import Button from "@material-ui/core/Button";
import s from "./LoginMenu.module.css";
import {googleAuthenticationUrl} from "../../../../api/backendInfo";
import {GoogleIcon} from "../../../common/GoogleIcon/GoogleIcon";

const LoginMenu = (props) => {

    const {handleLogin, handleSubmit, loading, errorText} = props;

    return (
        <div className={s.loginBlock}>
            {errorText && <p className={s.errorText}>{errorText}</p>}
            <form className={s.loginForm} onSubmit={handleSubmit(handleLogin)}>
                <Field label={"Email"} name={"email"} component={RenderTextFiled}/>
                <Field type={"password"} label={"Password"} name={"password"} component={RenderTextFiled}/>
                <Button type={'submit'} disabled={loading} variant={"outlined"}
                        startIcon={loading ? <CircularProgress size={10}/> : null}>{locales.ru.login}</Button>
                <a href={googleAuthenticationUrl} style={{margin: "auto"}}>
                    <GoogleIcon/>
                </a>
            </form>
            <Link to={'/registration'}>{locales.ru.signUp}</Link>
        </div>
    )
}

export default LoginMenu;