import { createRouter, createWebHistory } from "vue-router";
import api from "../api";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import Dashboard from "../components/Dashboard.vue";

const routes = [
  {
    path: "/",
    redirect: "/dashboard",
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

let isAuthenticated = false;

router.beforeEach(async (to, from, next) => {
  // Require auth path
  if (to.meta.requiresAuth) {
    if (to.name === "Login") {
      return next();
    }

    try {
      await api.get("/auth/me");
      isAuthenticated = true;
      next();
    } catch (err) {
      isAuthenticated = false;
      next("/login");
    }
  }

  // Guest path
  else if (to.meta.requiresGuest) {
    try {
      await api.get("/auth/me");
      next("/dashboard");
    } catch (err) {
      next();
    }
  } else {
    next();
  }
});

export default router;
