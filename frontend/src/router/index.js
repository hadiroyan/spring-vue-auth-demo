import { createRouter, createWebHistory } from "vue-router";
import api from "../api";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Dashboard from "../components/Dashboard.vue";
import AuthCallback from "../components/AuthCallback.vue";

const routes = [
  {
    path: "/",
    redirect: "/dashboard",
  },
  {
    path: "/auth/callback",
    name: "AuthCallback",
    component: AuthCallback,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: { requiresGuest: true },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: { requiresGuest: true },
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

let authCheckPromise = null;
let isAuthenticated = null;

async function checkAuth() {
  if (authCheckPromise) {
    return authCheckPromise;
  }

  if (isAuthenticated !== null) {
    return isAuthenticated;
  }

  authCheckPromise = api
    .get("/auth/me")
    .then(() => {
      isAuthenticated = true;
      return true;
    })
    .catch((err) => {
      console.warn("Auth check failed:", err);
      isAuthenticated = false;
      return false;
    })
    .finally(() => {
      authCheckPromise = null;
    });

  return authCheckPromise;
}

function resetAuthState() {
  isAuthenticated = null;
  authCheckPromise = null;
}

function setIsAuthenticated(value) {
  isAuthenticated = value;
  authCheckPromise = null;
}

router.beforeEach(async (to, from, next) => {
  if (to.path === "/auth/callback") {
    next();
    return;
  }

  const loggedIn = await checkAuth();

  if (to.meta.requiresAuth && !loggedIn) {
    return next("/login");
  }

  if (to.meta.requiresGuest && loggedIn) {
    return next("/dashboard");
  }

  next();
});

export { router as default, resetAuthState, setIsAuthenticated };
