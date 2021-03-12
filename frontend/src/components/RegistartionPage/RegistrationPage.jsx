import React, {useState} from "react";
import TextField from "@material-ui/core/TextField";
import {Field, reduxForm} from "redux-form";
import {authAPI} from "../../api/api";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import {useDispatch} from "react-redux";
import {setUserData} from "../../redux/reducers/authReducer";
import {locales} from "../../locales";
import s from "./RegistrationPage.module.css"
import {Link} from "react-router-dom";

const checkPassword = (password) => {
    if (password.length < 6 || password.length > 20) {
        return "Длинна пароля должна быть от 6 до 20 симолов"
    } else if (password.search('[a-zA-Zа-яА-Я]+') === -1) {
        return "Пароль должен содержать буквы"
    } else if (password.search('[0-9]+') === -1) {
        return "Пароль должен содержать цифры"
    }
}


const validate = (values) => {
    const errors = {};
    if (!values.email) {
        errors.email = locales.ru.requireError;
    } else if (!/^[^.0-9][a-z0-9.]{3,30}@\w+[.]\w+$/.test(values.email)) {
        errors.email = "Неверный email"
    }
    if (!values.password) {
        errors.password = locales.ru.requireError;
    } else {
        let passwordError = checkPassword(values.password);
        if (passwordError) {
            errors.password = passwordError;
        }
    }
    if (!values.confirmPassword) {
        errors.confirmPassword = locales.ru.requireError;
    } else if (values.password !== values.confirmPassword) {
        errors.confirmPassword = "Пароли не совпадают"
    }
    return errors;
}

const renderField = ({input, label, type, meta: {touched, invalid, error, warning}}) =>
    <TextField {...input} placeholder={label} label={label} type={type} error={touched && invalid}
               helperText={touched && error}
               variant={"outlined"}/>


const RegistrationPage = (props) => {

    const [shouldRedirect, setRedirect] = useState(false);
    const [userEmail, setUserEmail] = useState();
    const [timeUntilRedirect, setTimeUntilRedirect] = useState(5);
    const dispatch = useDispatch();

    const handleSubmit = (values) => {
        console.log(values);
        authAPI.registration(values)
            .then((response) => {
                setRedirect(true);
                setUserEmail(response.data.email)
                dispatch(setUserData(response.data))
            }).catch(reason => alert(reason));
    }

    const redirectTimeout = () => {
        setTimeout(() => {
            setTimeUntilRedirect(timeUntilRedirect - 1)
        }, 1000)
        if (timeUntilRedirect === 0) {
            props.history.push('/');
        }
    }

    return (
        <div className={s.registrationBlock}>
            <Dialog open={shouldRedirect}>
                <DialogContent>
                    <DialogContentText>
                        {locales.ru.promptAfterRegistration(userEmail)}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button
                        onClick={() => props.history.push('/')}>{'Go to main page (' + timeUntilRedirect + ')'}</Button>
                    {shouldRedirect && redirectTimeout()}
                </DialogActions>
            </Dialog>
            <form onSubmit={props.handleSubmit(handleSubmit)} className={s.registrationForm}>
                <Field name={'email'} component={renderField} label={locales.ru.email} type={'email'}/>
                <Field name={'password'} component={renderField} label={locales.ru.password} type={'password'}/>
                <Field name={'confirmPassword'} component={renderField} label={locales.ru.confirmPassword}
                       type={'password'}/>
                <Field name={'name'} component={renderField} label={locales.ru.name}/>
                <Button variant={"contained"} color={"primary"} type={"submit"}>{locales.ru.register}</Button>
                <Link style={{textAlign: "center"}} to={'/'}>На главную</Link>
            </form>
        </div>
    )
}

export default reduxForm({form: 'registration', validate})(RegistrationPage);