import React, {useEffect} from "react";
import {createMuiTheme, ThemeProvider} from "@material-ui/core";
import {connect} from "react-redux";
import {initializeApp} from "./redux/reducers/appReducer";
import CircularProgress from "@material-ui/core/CircularProgress";
import style from './App.module.css'
import withMessaging from "./withMessaging";
import {Route, Switch} from "react-router-dom";
import RegistrationPage from "./components/RegistartionPage/RegistrationPage";
import HeaderContainer from "./components/Header/HeaderContainer";
import MainPage from "./components/MainPage/MainPage";
import CarPageContainer from "./components/CarPage/CarPageContainer";
import SellCarPage from "./components/CreateAddPage/SaleCarPage";
import MessagesPage from "./components/MessagesPage/MessagesPage";
import MyCarsPage from "./components/MyCarPage/MyCarsPage";
import MyCarPage from "./components/MyCarPage/MyCarPage";
import LoginPage from "./components/LoginPage";
import NotFound from "./components/NotFound/NotFound";
import {withAuth, withRoleUser} from "./components/hocs/withPermissions";
import {VerifyAccountPage} from "./components/VerifyAccountPage/VerifyAccountPage";

const theme = createMuiTheme({
    palette: {
        primary: {
            light: '#4dabf5',
            main: '#2196f3',
            dark: '#1769aa'
        },
        secondary: {
            light: '#637bfe',
            main: '#3d5afe',
            dark: '#2a3eb1'

        },
    },
});

let App = (props) => {

    useEffect(() => {
        props.initializeApp();
    }, [])

    if (!props.initialized) {
        return (
            <div className={style.absolute}>
                <CircularProgress size={150}/>
            </div>)
    }

    return (
        <ThemeProvider theme={theme}>
            <Route exact path={'/'} component={HeaderContainer}/>
            <Route path={['/cars', '/messages']} component={HeaderContainer}/>
            <Switch>
                <Route exact path={'/registration'} component={RegistrationPage}/>
                <Route path={'/login'} component={LoginPage}/>
                <Route exact path={'/'} component={MainPage}/>
                <Route exact path={'/cars/sale'} component={withRoleUser(SellCarPage)}/>
                <Route exact path={'/cars/my'} component={withRoleUser(MyCarsPage)}/>
                <Route exact path={'/cars/my/:id'} component={withRoleUser(MyCarPage)}/>
                <Route path={'/cars/:id'}
                       render={(props) => !isNaN(props.match.params.id) && <CarPageContainer {...props}/>}/>
                <Route exact path={'/messages'} component={withAuth(MessagesPage)}/>
                <Route exact path={'/verify/:token'} component={VerifyAccountPage}/>
                <Route path={"*"} exact={true} component={NotFound}/>
            </Switch>
        </ThemeProvider>
    );
}

const mapStateToProps = (state) => ({
    initialized: state.app.initialized,
    auth: state.auth,
})

export default withMessaging(connect(mapStateToProps, {initializeApp})(App))