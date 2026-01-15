<template>
    <div class="min-h-screen bg-linear-to-br from-gray-100 to-blue-50 flex items-center justify-center p-4">
        <div class="w-full max-w-5xl bg-white rounded-2xl shadow-2xl overflow-hidden flex flex-col md:flex-row">
            <!-- Left Side - Login Form -->
            <div class="w-full md:w-1/2 p-8 md:p-12">
                <div class="flex items-center gap-3 mb-8">
                    <div class="w-10 h-10 bg-gray-900 rounded-full flex items-center justify-center">
                        <img src="../assets/electric_bolt-white.svg" alt="">
                    </div>
                    <span class="text-xl font-semibold text-gray-800">Demo Auth</span>
                </div>

                <h1 class="text-3xl font-bold text-gray-800 mb-2">Welcome Back!</h1>
                <p class="text-gray-500 mb-8">Sign in to continue your journey</p>

                <form @submit.prevent="handleLogin" class="space-y-6">
                    <!-- Email -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                        <div class="relative">
                            <input v-model="loginForm.email" type="text" placeholder="Enter your email"
                                class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />

                            <span class="absolute right-3 top-1/2 -translate-y-1/2 ">
                                <img src="../assets/mail-gray-500.svg" alt="">
                            </span>
                        </div>
                    </div>

                    <!-- Password -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Password</label>
                        <div class="relative">
                            <input v-model="loginForm.password" :type="showPassword ? 'text' : 'password'"
                                placeholder="Enter your password"
                                class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            <span @click="togglePassword"
                                class="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer z-10">
                                <img :src="showPassword ? eyeOff : eye" alt="">
                            </span>
                        </div>
                    </div>

                    <!-- Sign In Button -->
                    <button type="submit"
                        class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition flex items-center justify-center gap-2">
                        Sign In
                        <span>
                            <img src="../assets/arrow_right-white.svg" alt="">
                        </span>
                    </button>
                </form>

                <!-- Google OAuth Login -->
                <button
                    class="w-full mt-4 bg-gray-200 text-gray-600 hover:bg-blue-600 hover:text-white rounded-lg font-semibold p-3 flex items-center justify-center gap-2"
                    @click="loginWithGoogle">
                    <img src="../assets/google-24.svg" alt="Google" />
                    Login with Google
                </button>

                <!-- Sign Up Link -->
                <p class="mt-6 text-center text-gray-600">
                    Don't have an account?
                    <a href="/register" class="text-blue-600 hover:underline font-semibold">
                        Sign up
                    </a>
                </p>
            </div>

            <!-- Right Side -->
            <div
                class="hidden md:flex md:w-1/2 bg-linear-to-br from-blue-800 to-blue-600 p-12 items-center justify-center text-white relative overflow-hidden">
                <div class="absolute top-10 right-10 w-32 h-32 bg-white/10 rounded-full blur-3xl"></div>
                <div class="absolute bottom-10 left-10 w-40 h-40 bg-white/10 rounded-full blur-3xl"></div>

                <div class="relative z-10 text-center space-y-6">
                    <div class="space-y-4">
                        <p class="text-2xl md:text-3xl font-bold leading-relaxed">
                            "Secure authentication starts here."
                        </p>
                        <p class="text-xl md:text-2xl font-semibold leading-relaxed">
                            "Built with Spring Boot and Vue.js for modern web security."
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api'

import eye from '../assets/visibility-gray-500.svg'
import eyeOff from '../assets/visibility_off-gray-500.svg'
import { setIsAuthenticated } from '../router';

const router = useRouter();
const loginForm = reactive({
    email: '',
    password: ''
});

const error = ref(null);
const showPassword = ref(false);

const handleLogin = async () => {
    console.log("Handle login .....")

    try {
        // console.log('Login:', loginForm);
        const response = await api.post('/auth/login', loginForm);
        console.log(response.data);
        setIsAuthenticated(true);

        // Go to dashboard
        router.push('/dashboard');
    } catch (err) {
        error.value = 'User failed to login'
        console.error(`Failed login ${err}`)
    }
};

const loginWithGoogle = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

const togglePassword = () => {
    showPassword.value = !showPassword.value
}

</script>