<template>
    <div class="min-h-screen bg-linear-to-br from-gray-50 to-blue-50">
        <!-- Header/Navbar -->
        <header class="bg-white shadow-sm">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                <div class="flex justify-between items-center">
                    <!-- Logo & Brand -->
                    <div class="flex items-center gap-3">
                        <div class="w-10 h-10 bg-gray-900 rounded-full flex items-center justify-center">
                            <img src="../assets/electric_bolt-white.svg" alt="">
                        </div>
                        <span class="text-xl font-semibold text-gray-800">Auth App</span>
                    </div>

                    <!-- User Info & Logout -->
                    <div class="flex items-center">
                        <button @click="handleLogout"
                            class="flex items-center gap-2 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition font-medium cursor-pointer">
                            <img src="../assets/logout.svg" alt="">
                            <span class="hidden sm:inline">Logout</span>
                        </button>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <!-- Welcome Section -->
            <div class="bg-linear-to-r from-blue-800 to-blue-600 rounded-2xl p-8 text-white mb-8">
                <h1 class="text-3xl font-bold mb-2">Welcome back, {{ user.firstName }}! 👋</h1>
                <p class="text-blue-100">This is dasboard for Auth app</p>
            </div>

            <div class="flex justify-between bg-white p-6 mb-6 rounded-xl shadow-lg">
                <div class="flex items-center gap-4 p-4">
                    <img src="../assets/person.svg" alt="" class="w-12 bg-blue-600 rounded-full p-2">
                    <div>
                        <h2 class="font-semibold text-gray-800">{{ user.firstName }} {{ user.lastName }}</h2>
                        <h3 class="text-gray-600">{{ user.email }}</h3>
                    </div>
                </div>

                <div class="flex items-center gap-1">
                    <img src="../assets/call-black.svg" alt="">
                    <h3 class="text-gray-600 text-lg">(number-phone)</h3>
                </div>
            </div>
        </main>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '../api'

const router = useRouter();
const error = ref(null)
const user = ref({
    email: '',
    firstName: '',
    lastName: '',
});

onMounted(async () => {
    try {
        const response = await api.get('/users/profile')
        user.value.email = response.data.email;
        user.value.firstName = response.data.firstName;
        user.value.lastName = response.data.lastName;
    } catch (err) {
        error.value = 'Failed to load profile'
        console.error(`Failed to load profile: ${err}`)
    }
})

const handleLogout = async () => {
    // Logout logic
    await api.post('/auth/logout');
    router.push('/login')
};
</script>