platform :ios, '9.0'
use_frameworks!

xcodeproj './ios/ReactNativeSimpleAuthExample.xcodeproj'

target 'ReactNativeSimpleAuthExample' do
  pod 'SimpleAuth/Facebook'
  pod 'SimpleAuth/GoogleWeb'
  pod 'SimpleAuth/Twitter'
  pod 'SimpleAuth/Instagram'
  pod 'SimpleAuth/Tumblr'
  pod 'SimpleAuth/LinkedInWeb'
end

post_install do |_|
  work_dir = Dir.pwd
  file_name = "#{work_dir}/Pods/Target\ Support\ Files/SimpleAuth/SimpleAuth.xcconfig"
  config = File.read(file_name)
  new_config = config.gsub(/HEADER_SEARCH_PATHS = "/, 'HEADER_SEARCH_PATHS = "${PODS_ROOT}" "')
  File.open(file_name, 'w') { |file| file << new_config }
end
