<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-50">
        <div class="flex flex-col items-center gap-4">
            <!-- Spinner -->
            <svg class="animate-spin h-16 w-16 text-blue-600" xmlns="http://www.w3.org/2000/svg" fill="none"
                viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z" />
            </svg>

            <!-- Text -->
            <p class="text-gray-700 text-md font-medium">
                Completing login with Google...
            </p>
        </div>
    </div>
</template>

<script setup>
import api from '../api';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
onMounted(async () => {
    try {
        const response = await api.get("/auth/me");

        if (response.data.email) {
            setTimeout(() => {
                router.replace('/dashboard');
            }, 500);
        } else {
            router.replace('/login');
        }
    } catch (err) {
        console.error('Authentication verification failed:', err);
        router.replace('/login');
    }
})

</script>

<style scoped></style>