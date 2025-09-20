#!/bin/bash

echo "🚀 Starting User Management Application..."
echo "📍 Application will be available at: http://localhost:8080"
echo ""
echo "🎯 Test Users:"
echo "👑 Admin: admin@example.com / admin123"
echo "👔 Manager: manager@example.com / manager123"
echo "👤 User: user@example.com / user123"
echo ""
echo "🔗 Quick Links:"
echo "🏠 Home: http://localhost:8080/"
echo "🔐 Login: http://localhost:8080/login"
echo "🏥 Health: http://localhost:8080/actuator/health"
echo "🗄️ H2 Console: http://localhost:8080/h2-console"
echo ""
echo "⏹️ Press Ctrl+C to stop the application"
echo ""

# Start the application
./gradlew bootRun
