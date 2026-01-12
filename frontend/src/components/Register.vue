<template>
    <div class="h-screen bg-linear-to-br from-gray-100 to-blue-50 flex items-center justify-center p-4">
        <div
            class="w-full max-w-7xl max-h-[95vh] bg-white rounded-2xl shadow-2xl overflow-hidden flex flex-col md:flex-row">
            <!-- Left Side - Register Form -->
            <div class="w-full md:w-1/2 p-4 md:p-8 overflow-y-auto">
                <div class="flex items-center gap-3 mb-8">
                    <div class="w-10 h-10 bg-gray-900 rounded-full flex items-center justify-center">
                        <span class="material-icons text-white">
                            bolt
                        </span>
                    </div>
                    <span class="text-xl font-semibold text-gray-800">Demo Auth</span>
                </div>

                <h1 class="text-3xl font-bold text-gray-800 mb-2">Create Your Account</h1>
                <p class="text-gray-500 mb-4">Sign up to start your driving journey</p>

                <form @submit.prevent="handleRegister" class="space-y-5">

                    <div class="relative flex flex-row items-center space-x-5">
                        <!-- First Name -->
                        <div class="basis-1/2">
                            <label class="block text-sm font-medium text-gray-700 mb-2">First Name</label>
                            <div class="relative">
                                <input v-model="registerForm.firstName" type="text" placeholder="Enter your first name"
                                    required
                                    class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            </div>
                        </div>

                        <!-- Last Name -->
                        <div class="basis-1/2">
                            <label class="block text-sm font-medium text-gray-700 mb-2">Last Name</label>
                            <div class="relative">
                                <input v-model="registerForm.lastName" type="text" placeholder="Enter your last name"
                                    required
                                    class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            </div>
                        </div>
                    </div>

                    <!-- Email -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                        <div class="relative">
                            <input v-model="registerForm.email" type="email" placeholder="Enter your email" required
                                class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            <span class="material-icons absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
                                email
                            </span>
                        </div>
                    </div>

                    <!-- Password -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Password</label>
                        <div class="relative">
                            <input v-model="registerForm.password" :type="showPassword ? 'text' : 'password'"
                                placeholder="Enter your password" required
                                class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            <span @click="togglePassword"
                                class="material-icons absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 cursor-pointer z-10">
                                {{ showPassword ? 'visibility_off' : 'visibility' }}
                            </span>
                        </div>
                    </div>

                    <!-- Phone -->
                    <div>
                        <label class="block text-sm font-medium text-gray-700 mb-2">Number Phone</label>
                        <div class="relative">
                            <input v-model="registerForm.phone" type="tel" placeholder="Enter your number phone"
                                required
                                class="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition" />
                            <span class="material-icons absolute right-3 top-1/2 -translate-y-1/2 text-gray-500">
                                phone
                            </span>
                        </div>
                    </div>

                    <!-- Sign Up Button -->
                    <button type="submit"
                        class="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition disabled:opacity-50 disabled:cursor-not-allowed">
                        <span>Create Account</span>
                    </button>
                </form>

                <!-- Sign In Link -->
                <p class="mt-6 mb-4 text-center text-gray-600">
                    Already have an account?
                    <a href="/login" class="text-blue-600 hover:underline font-semibold">
                        Sign in
                    </a>
                </p>
            </div>

            <!-- Right Side -->
            <div
                class="hidden md:flex md:w-1/2 bg-linear-to-br from-blue-600 to-blue-700 p-12 items-center justify-center text-white relative overflow-hidden">
                <div class="absolute top-10 right-10 w-32 h-32 bg-white/10 rounded-full blur-3xl"></div>
                <div class="absolute bottom-10 left-10 w-40 h-40 bg-white/10 rounded-full blur-3xl"></div>

                <div class="relative z-10 text-center space-y-6">
                    <div class="space-y-4">
                        <p class="text-2xl md:text-3xl font-bold leading-relaxed">
                            "Spring Boot × Vue.js"
                        </p>
                        <p class="text-xl md:text-2xl font-semibold leading-relaxed">
                            "Authentication Demo Project"
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';

const showPassword = ref(false);

const registerForm = reactive({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phone: ''
});

const handleRegister = () => {
    console.log('Register:', registerForm);
    // Registration logic
};

const togglePassword = () => {
    showPassword.value = !showPassword.value
}


</script>